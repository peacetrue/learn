/**
 * 填充内容，以测试滚动效果。
 *
 * @param container 容器
 * @param [count] 数目
 * @param [text] 文本内容
 * @param [marginLeft] 左填充
 */
function fullContent(container, count, text, marginLeft) {
    count = count || 100;
    text = text || 'text';
    marginLeft = marginLeft ? `margin-left: ${marginLeft}px` : '';
    for (let i = 0; i < count; i++) {
        container.innerHTML += `<div style="${marginLeft}">${text}</div>`;
    }
}

/**
 * 填充内容，以测试滚动效果。
 *
 * @param element 按钮元素
 * @param [lineCount] 内容行数
 */
function collapseContent(element, lineCount) {
    lineCount = lineCount || 100;
    let container = element.parentNode;
    let contentFulled = container.getAttribute("contentFulled") || "false";
    if (contentFulled === "true") {
        container.innerHTML = element.outerHTML;
    } else {
        fullContent(container, lineCount, element.innerHTML);
    }
    container.setAttribute("contentFulled", ({"true": "false", "false": "true"})[contentFulled]);
}

function changeHref(element, hrefs) {
    let href = element.getAttribute('href');
    let index = hrefs.indexOf(href);
    element.setAttribute('href', hrefs[index === hrefs.length - 1 ? 0 : ++index]);
}

/**
 * @param options tagName,attributes,innerHTML,innerText,children
 * @return {HTMLElement}
 */
function createElement(options) {
    console.info("createElement: ", options)
    let element = document.createElement(options.tagName);
    if (options.attributes) {
        for (let name in options.attributes) {
            console.info("setAttribute: ", name, options.attributes[name])
            element.setAttribute(name, options.attributes[name]);
        }
    }
    if (options.innerHTML) element.innerHTML = options.innerHTML;
    if (options.innerText) element.innerText = options.innerText;
    if (options.children) options.children.forEach(child => element.appendChild(child));
    return element;
}

function setLink(options) {
    console.info("setLink: ", options)
    $(`#${options.attributes.id}`).remove();
    if (options.appendChild) {
        options.attributes.rel = "stylesheet";
        document.head.appendChild(createElement({tagName: 'link', ...options}));
    }
}

window.$ && $(function () {
    $("#draggable").draggable();
    $('[data-widget=link]').change(function () {
        let options = $(this).data("options");
        options = JSON.parse(options.replace(/'/g, '"'));
        !(options instanceof Array) && (options = [options]);
        console.info("options: ", options)
        options.forEach(item => {
            let isCheckbox = this.type === 'checkbox';
            if (isCheckbox) {
                return setLink({appendChild: this.checked, attributes: item});
            }

            let isSelect = this.tagName === 'SELECT';
            if (isSelect) {
                item.href = item.href || this.value;
                setLink({appendChild: !!this.value, attributes: item});
            }
        });
    }).change();
});
