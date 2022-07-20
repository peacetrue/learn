/**
 * 填充内容，以测试滚动效果。
 *
 * @param element 按钮元素
 * @param [lineCount] 内容行数
 */
function fullContent(element, lineCount) {
    lineCount = lineCount || 100;
    let container = element.parentNode;
    let contentFulled = container.getAttribute("contentFulled") || "false";
    if (contentFulled === "true") {
        container.innerHTML = element.outerHTML;
    } else {
        for (let i = 0; i < lineCount; i++) {
            container.innerHTML += `<div>${element.innerText}</div>`;
        }
    }
    container.setAttribute("contentFulled", ({"true": "false", "false": "true"})[contentFulled]);
}
