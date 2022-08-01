//@formatter:off
document.body.insertAdjacentHTML('beforeend',
`<div id="draggable" class="ui-widget-content">
        <div>可拖拽：</div>
        <div>
            <label>布局：
                <select data-widget="link" data-options="[{'id':'layout','href':'tmb-base.css'}]">
                    <option value="">请选择</option>
                    <option value="tmb-base.css">弹性布局</option>
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
    <link rel="stylesheet" href="../libs/jquery-ui.css"/>
    <link rel="stylesheet" href="../libs/draggable.css"/>
<!--    <script src="../libs/jquery-3.6.0.js"></script>-->
<!--    <script src="../libs/jquery-ui.js"></script>-->
<!--    <script src="../libs/common.js"></script>-->
`
);
