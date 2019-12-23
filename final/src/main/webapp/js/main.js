function changeLang(name) {
    document.cookie = "lang=" + name;
    window.location.reload(true);
}

