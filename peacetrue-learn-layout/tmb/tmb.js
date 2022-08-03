//@formatter:off
document.body.insertAdjacentHTML('beforeend',
`<div id="draggable" class="ui-widget-content">
        <div>可拖拽：</div>
        <div>
            <label>布局：
                <select data-widget="link" data-options="[{'id':'layout','href':'tmb-flex.css'}]">
                    <option value="">请选择</option>
                    <option value="tmb-flex.css">弹性布局</option>
                </select>
            </label>
        </div>
        <div>
            <label>着色：
                <input type="checkbox" data-widget="link" data-options="[{'id':'color','href':'tmb-color.css'}]">
            </label>
        </div>
        <div>
            <label>尺寸：
                <input type="checkbox" data-widget="link" data-options="[{'id':'size','href':'tmb-size.css'}]">
            </label>
        </div>
    </div>
    <link rel="stylesheet" href="../common/jquery-ui.css"/>
    <link rel="stylesheet" href="../common/draggable.css"/>
<!--    <script src="../common/jquery-3.6.0.js"></script>-->
<!--    <script src="../common/jquery-ui.js"></script>-->
<!--    <script src="../common/common.js"></script>-->
`
);
