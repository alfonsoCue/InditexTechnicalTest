package com.inditex.prices;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest {
    @Test
    void DDD_architectureRulesTest()
    {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.inditex.prices");

        ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
            .layer("Infrastructure").definedBy("com.inditex.prices.infrastructure..")
            .layer("Application").definedBy("com.inditex.prices.application..")
            .layer("Domain").definedBy("com.inditex.prices.domain..")

            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure");

        layer_dependencies_are_respected.check(importedClasses);
    }

}
