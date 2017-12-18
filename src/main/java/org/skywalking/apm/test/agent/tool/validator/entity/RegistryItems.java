package org.skywalking.apm.test.agent.tool.validator.entity;

import java.util.List;

public interface RegistryItems {
    List<RegistryApplication> applications();

    List<RegistryInstance> instances();

    List<RegistryOperationName> operationNames();
}
