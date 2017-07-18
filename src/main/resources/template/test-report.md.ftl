# Test Report - ${testReport.testTime}

[![TestStatus Badge](https://img.shields.io/badge/test--status-${testReport.status.desc}-${testReport.status.color}.svg)](${testReport.portalURL})

- agent branch: **[${testReport.agentBranch}](https://github.com/wu-sheng/sky-walking/tree/${testReport.agentBranch})**
- agent commit: **[${testReport.agentCommit}](https://github.com/wu-sheng/sky-walking/commit/${testReport.agentCommit})**

<#if (testReport.testCases?size > 0)>
| case name     | component|status |
|:------------- |:--------|:-------|
    <#list testReport.testCases as item>
| ${item.caseName}  | <#list item.components as component>${component_index + 1}. ${component.name}<br/></#list>|[![TestStatus Badge](https://img.shields.io/badge/test--status-${item.status.desc}-${item.status.color}.svg)](${testReport.portalURL}) |
    </#list>
<#else >
  <h5>无测试用例</h5>
</#if>
