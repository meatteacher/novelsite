function showTab(tabId) {
    const tabs = document.querySelectorAll(".admin_tab_content");
    tabs.forEach(tab => tab.style.display = "none");
    document.getElementById(tabId).style.display = "block";
}

document.addEventListener("DOMContentLoaded", function () {
    showTab("episodes"); // 기본 탭
});