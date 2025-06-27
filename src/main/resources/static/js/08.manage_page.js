function showTab(tabName) {
    const tabs = document.querySelectorAll(".admin_tab_content");
    tabs.forEach(tab => tab.style.display = "none");

    document.getElementById(tabName).style.display = "block";
}
