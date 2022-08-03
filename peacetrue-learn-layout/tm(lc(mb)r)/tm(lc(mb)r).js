document.body.insertAdjacentHTML('beforeend',
    `<div id="draggable" class="ui-widget-content">
            <div>可拖拽：</div>
            <div>
                <label>布局：
                    <select data-widget="link"
                            data-options="[{'id':'layout','href':'../tmb/tmb-flex.css'},{'id':'layout-2','href':'tm(lc(mb)r)-flex.css'}]">
                        <option value="flex">弹性布局</option>
                    </select>
                </label>
            </div>
            <div>
                <label>变体：
                    <select data-widget="link" data-options="[{'id':'layout-vars'}]">
                        <option value="">请选择</option>
                        <option value="tm(lc(mb)r)-1.css">各个区域独立滚动</option>
                        <option value="tm(lc(mb)r)-2.css">article 区域使用 main 区域滚动条</option>
                    </select>
                </label>
            </div>
        </div>`
);
