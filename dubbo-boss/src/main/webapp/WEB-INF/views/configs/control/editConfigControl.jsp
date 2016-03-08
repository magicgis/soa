<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<style type="text/css">
.configlist th {
  background: #eef3f7;
}
.configlist select {
  width: 60px;
}
</style>
<div id="_editConfigBlock" style="display:none">
  <div class="information">
    <table style="display:none">
      <tr id="_TPL_LIST">
        <td><input class="input_text" type="text" style="width:200px;" name="_listValue" value=""></td>
        <td><a href="javascript:void(0);" onclick="deleteRow(this);">删除</a></td>
      </tr>
      <tr id="_TPL_MAP">
        <td><input class="input_text" type="text" style="width:200px;" name="_mapKey" value=""></td>
        <td><input class="input_text" type="text" style="width:200px;" name="_mapValue" value=""></td>
        <td><a href="javascript:void(0);" onclick="deleteRow(this);">删除</a></td>
      </tr>
      <tr id="_TPL_STRUCTURE">
        <td><input class="input_text" type="text" style="width:200px;" name="_structureKey" value=""></td>
        <td><input class="input_text" type="text" style="width:200px;" name="_structureValue" value=""></td>
        <td>
          <select name="_structureDataType" class="select">
            <c:forEach items="${dataTypes}" var="dataType">
              <option value="${dataType.key}">${dataType.value}</option>
            </c:forEach>
          </select>
        </td>
        <td><a href="javascript:void(0);" onclick="deleteRow(this);">删除</a></td>
      </tr>
    </table>
    <div class="clear"></div>
    <form name="editConfigForm" id="editConfigForm" method="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">所属应用：</label>
            <div class="select_border">
              <div class="container">
                <select id="application" name="application" class="select">
                  <option selected="selected" value="commoncfg">公用</option>
                  <c:forEach items="${applications}" var="application">
                    <option value="${application.appName}">${application.appName}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </li>
          <li>
            <label class="text_tit">配置键：</label>
            <input class="input_text" type="text" style="width:200px;" name="configKey" value="${app.configKey}" readonly="true">
          </li>
          <li>
            <label class="text_tit">配置名：</label>
            <input class="input_text" type="text" style="width:200px;" name="configName" value="${app.configName}">
            <input type="hidden" name="isAdd" value="">
          </li>
          <li>
            <label class="text_tit">值类型：</label>
            <div class="select_border">
              <div class="container">
                <select id="_valueType" name="valueType" class="select">
                  <c:forEach items="${valueTypes}" var="valueType">
                    <option value="${valueType.key}">${valueType.value}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </li>
          <li id="_dataTypeBlock">
            <label class="text_tit">数据类型：</label>
            <div class="select_border">
              <div class="container">
                <select id="_dataType" name="dataType" class="select">
                  <c:forEach items="${dataTypes}" var="dataType">
                    <option value="${dataType.key}">${dataType.value}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </li>
          <li id="VALUE_BLOCK" name="configValueBlock">
            <label class="text_tit">配置值：</label>
            <input class="input_text" type="text" style="width:200px;" id="_configValue" name="configValue" value="">
          </li>
          <li id="LIST_BLOCK" style="display:none;" name="configValueBlock">
            <label class="text_tit">配置值：</label>
            <table class="configlist">
              <tr>
                <th>值</th>
                <th>操作</th>
              </tr>
              <tr>
                <td><input class="input_text" type="text" style="width:200px;" name="_listValue" value=""></td>
                <td><a href="javascript:void(0);" onclick="addRow(this, 'LIST')">添加</a></td>
              </tr>
            </table>
          </li>
          <li id="MAP_BLOCK" style="display:none;" name="configValueBlock">
            <label class="text_tit">配置值：</label>
            <table class="configlist">
              <tr>
                <th>键</th>
                <th>值</th>
                <th>操作</th>
              </tr>
              <tr>
                <td><input class="input_text" type="text" style="width:200px;" name="_mapKey" value=""></td>
                <td><input class="input_text" type="text" style="width:200px;" name="_mapValue" value=""></td>
                <td><a href="javascript:void(0);" onclick="addRow(this, 'MAP')">添加</a></td>
              </tr>
            </table>
          </li>
          <li id="STRUCTURE_BLOCK" style="display:none;" name="configValueBlock">
            <label class="text_tit">配置值：</label>
            <table class="configlist">
              <tr>
                <th>键</th>
                <th>值</th>
                <th width="60px">类型</th>
                <th>操作</th>
              </tr>
              <tr>
                <td><input class="input_text" type="text" style="width:200px;" name="_structureKey" value=""></td>
                <td><input class="input_text" type="text" style="width:200px;" name="_structureValue" value=""></td>
                <td>
                  <select name="_structureDataType" class="select">
                    <c:forEach items="${dataTypes}" var="dataType">
                      <option value="${dataType.key}">${dataType.value}</option>
                    </c:forEach>
                  </select>
                </td>
                <td><a href="javascript:void(0);" onclick="addRow(this, 'STRUCTURE')">添加</a></td>
              </tr>
            </table>
          </li>
          <li>
            <label class="text_tit">启用：</label>
            <div class="select_border">
              <div class="container">
                <select id="enabled" name="enabled" class="select">
                  <option value="true">启用</option>
                  <option value="false">禁用</option>
                </select>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
