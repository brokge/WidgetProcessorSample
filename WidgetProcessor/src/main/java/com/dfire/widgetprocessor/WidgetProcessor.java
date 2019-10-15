package com.dfire.widgetprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;


@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.dfire.widgetprocessor.Widget"})
public class WidgetProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private String mSupperClsQualifiedName;

    private Map<String, String> mOptions;

    /**
     * 获取到Types、Filer、Messager、Elements
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mTypeUtils = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
        mOptions = processingEnvironment.getOptions();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String classModuleName = "";
        if (!mOptions.isEmpty()) {
            classModuleName = mOptions.get("DFIRE_MODULE_NAME");
            mMessager.printMessage(Diagnostic.Kind.NOTE, "classModuleName: " + classModuleName);
        }
        String className = classModuleName + "WidgetsFactory";
        Set<TypeElement> types = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Widget.class));
        if (types.isEmpty()) {
            return false;
        }

        for (TypeElement type : types) {
            mSupperClsQualifiedName = type.getSuperclass().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "Widget的父类是: " + mSupperClsQualifiedName);
            break;
        }
        int endIndex = mSupperClsQualifiedName.indexOf("<");


        ClassName baseFragment = ClassName.bestGuess(endIndex != -1 ? mSupperClsQualifiedName.substring(0, endIndex) : mSupperClsQualifiedName);

        ClassName superInterface = ClassName.get("com.dfire.basewidgetfactory", "IWidgetFactory");
        TypeSpec.Builder tb = TypeSpec.classBuilder(className).addSuperinterface(superInterface).addModifiers(PUBLIC, FINAL).addJavadoc("@ 实例化工厂 此类由apt自动生成");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("create").addAnnotation(MemoryCache.class)
                .addJavadoc("@此方法由apt自动生成" + mSupperClsQualifiedName)
                .returns(baseFragment).addModifiers(PUBLIC).addException(IllegalAccessException.class).addException(InstantiationException.class)
                .addParameter(String.class, "component").addParameter(String.class, "jsonNode");

        List<ClassName> mList = new ArrayList<>();
        CodeBlock.Builder blockBuilder = CodeBlock.builder();
        //括号开始
        blockBuilder.beginControlFlow(" switch (component)");
        try {
            String moduleName = Utils.PackageName;
            for (TypeElement element : types) {
                //moduleName = Utils.getPackageName(mElementUtils, element);
                mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                if (!Utils.isValidClass(mMessager, element)) {
                    return true;
                }
                Widget annotation = element.getAnnotation(Widget.class);
                //获取Widget注解的value
                String mId = annotation.value();
                ClassName currentType = ClassName.get(element);

                if (mList.contains(currentType)) {
                    continue;
                }
                mList.add(currentType);
                //初始化Presenter
                blockBuilder.addStatement("case $S: \n return $T.instance(jsonNode)", mId, currentType);
            }
            blockBuilder.addStatement("default: break");
            blockBuilder.endControlFlow();
            methodBuilder.addCode(blockBuilder.build());
            methodBuilder.addCode("return null;\n");
            tb.addMethod(methodBuilder.build());

            // 生成源代码
            JavaFile javaFile = JavaFile.builder(moduleName, tb.build()).build();
            javaFile.writeTo(mFiler);
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
