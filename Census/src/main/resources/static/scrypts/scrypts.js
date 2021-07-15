function getPage(page) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    var limit = urlParams.get('limit');
    var namePart = urlParams.get('namePart');
    var newLink = "?page=" + page;
    if (limit != null)
        newLink = newLink + "&limit=" + limit;
    if (namePart != null)
        newLink = newLink + "&namePart=" + namePart;
    window.location.href = newLink;
}

function changeLanguage(e){
	 var urlParams = new URLSearchParams(window.location.search);
	 urlParams.set('localData',e);
	 window.location.search = urlParams;
}

function addItem() {
    var currentHref, neededHref, pathArray;
    currentHref = window.location.href;
    pathArray = window.location.pathname.split('/');
    if (currentHref.indexOf("categories") > -1) {
        window.location.href = "/items/categories/" + pathArray[3] +
            "/add";
    } else if (currentHref.indexOf("locations") > -1) {
        window.location.href = "/items/locations/" + pathArray[3] +
            "/add";
    } else
        window.location.href = "/items/add";
}

function searchPage() {
    var input = document.getElementById("search");
        input.oninput = function() {
        	 var res = input.value.toString();
        	 var currentHref = new URL(window.location.href);
        	 if (res.length == 0) {
                 currentHref.searchParams.delete('namePart');
                 window.location.href = currentHref;
        	 }
        	 
        	if (localStorage.getItem("checkBox") == "false"){
            if (res.length >= 3) {
                currentHref.searchParams.set('namePart', res);
                currentHref.searchParams.set('page', 1);
                window.location.href = currentHref;
            } else if (res.length == 0) {
                currentHref.searchParams.delete('namePart');
                window.location.href = currentHref;
            } else
                document.getElementById('searchValidation').innerHTML = "Please, enter first 3 letters";
        	}
        };
       
}

function clearStorageAndBack() {
    window.localStorage.removeItem("nameTxt");
    window.localStorage.removeItem("descTxt");
    window.localStorage.removeItem("location-text");
    window.localStorage.removeItem("category-text");
    window.localStorage.removeItem("location-value");
    window.localStorage.removeItem("category-value");
    window.location.href = "/items/my";
}