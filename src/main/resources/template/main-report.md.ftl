# Test Report

[![TestStatus Badge](https://img.shields.io/badge/status-${testReport.status.desc}-${testReport.status.color}.svg)]()
[![TestStatus Badge](https://img.shields.io/badge/report--date-${testReport.testDate}-brightgreen.svg)]()
[![TestStatus Badge](https://img.shields.io/badge/branch-${testReport.testBranch}-brightgreen.svg)]()
[![TestStatus Badge](https://img.shields.io/badge/commitId-${testReport.commitId}-brightgreen.svg)]()

<#list testReport.caseScenarios as caseScenarios>
## ${caseScenarios.testFramework} Scenario

[![TestStatus Badge](https://img.shields.io/badge/status-${caseScenarios.status.desc}-${caseScenarios.status.color}.svg)]()
${caseScenarios.testFramework} Scenario contain **${(caseScenarios.cases?size)}** cases. The scenario use the following component. and click [me](#${caseScenarios.testFramework?lower_case}-support-version) to get the support version list.

<#list caseScenarios.cooperativeFrameworks as cooperativeFramework>
- `${cooperativeFramework.name}`
</#list>

</#list>

## Support Version

<#list testReport.caseScenarios as caseScenarios>
### ${caseScenarios.testFramework} Support Version

Here is the support version list of the scenario.

| supported version     | status |
|:------------- |:-------:|
<#list caseScenarios.cases as item>
| ${item.caseName}  | [![TestStatus Badge](https://img.shields.io/badge/test--status-${item.status.desc}-${item.status.color}.svg)]() |
</#list>

</#list>

see build log: