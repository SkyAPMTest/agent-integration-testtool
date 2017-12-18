# Test Report - ${testReport.testDate}

[![TestStatus Badge](https://img.shields.io/badge/status-${testReport.status.desc}-${testReport.status.color}.svg)]()

- Branch Name : **[${testReport.testBranch}](https://github.com/apache/incubator-skywalking/tree/${testReport.testBranch})**
- Commit Id : **[${testReport.commitId}](https://github.com/apache/incubator-skywalking/commit/${testReport.commitId})**

## Cases List

<#list testReport.caseScenarios as caseScenarios>
| Framework | Status | Cases|
|:-----|:-----:|:-----:|
|${caseScenarios.testFramework}|[![TestStatus Badge](https://img.shields.io/badge/status-${caseScenarios.status.desc}-${caseScenarios.status.color}.svg)]()| [click me](#${caseScenarios.testFramework?lower_case}) |
</#list>

<#list testReport.caseScenarios as caseScenarios>
## ${caseScenarios.testFramework}

### <#list caseScenarios.categoryForProjects as project>
|  Version     | Supported | UnSupported|
|:------------- |:-------:|:-----:|
<#list project.testCases as item>
| ${item.caseName}  | <#if item.success>:heavy_check_mark:</#if>|<#if !item.success>:heavy_check_mark:</#if>|
</#list>
</#list>

</#list>