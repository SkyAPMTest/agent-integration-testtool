package org.skywalking.apm.test.agent.tool.caseassert.entity;

import java.util.List;

public interface RegistryItems {
    List<RegistryApplication> applications();

    List<RegistryInstance> instances();

    List<RegistryOperationName> operationNames();
}
