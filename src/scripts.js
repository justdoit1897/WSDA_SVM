// GENERALI

function getCookie(cookieName){
    let cookies = decodeURIComponent(document.cookie).split(";")
    let name = cookieName+"="

    for(let i in cookies){
        let cookie = cookies[i]
        while(cookie.charAt(0) == ' '){
            cookie = cookie.substring(1)
        }

        if(cookie.indexOf(name) == 0){
            return cookie.substring(name.length, cookie.length)
        }
    }
}

function requestContent(element, content){
    $.ajax({
        type: 'GET',
        url: './RequestContentServlet',
        data: {
            content: content
        },
        success: function (data) {
            element.html(data)
        }
    })
}

function customClick(event, element, content){
    event.preventDefault()

    requestContent(element, content)
}

function callLogout(event){
    event.preventDefault()

    $.ajax({
        method: 'GET',
        url: './LogoutServlet',
        success: function () {
            window.location.reload()
        }
    })
}

function buildModaleCliente(object){
    $("#modal-title").html(object["titolo"])
    $("#modal-text").html(object["corpo"])
    if(object["esito"] == "successo") {
        $("#modal-button").click((ev) => {
            ev.preventDefault()

            window.location.reload()
        })
    }
    $("#customer-modal").toggle()
}

// Specifiche Cliente

function callDisconnessioneLatoUtente(event){
    event.preventDefault()

    $.ajax({
        type: 'GET',
        url: './DisconnessioneMacchina',
        success: function(data) {
            let object = JSON.parse(data)

            buildModaleCliente(object)
        }
    })
}

function aggiornaStatoLatoUtente(){
    $.ajax({
        type: 'GET',
        url: './CheckConnessioni',
        success: function (data){
            let object = JSON.parse(data)
            if(object["azione"] == "ricarica") {
                window.location.reload()
            }
        }
    })
}

// Specifiche macchina

function aggiornaStatoLatoMacchina(oldStatus){

    $.ajax({
        type: 'GET',
        url: './CheckStatus',
        success: function (data){
            if(oldStatus != data
                && (data == 'Disponibile'||data == 'Non disponibile')){
                window.location.reload()
            }
        }
    })
}

function disconnettiUtente(){
    $.ajax({
        type: 'GET',
        url: './DisconnettiUtente',
        success: function (data){
            let object = JSON.parse(data)

            $("#modal-title").html(object["titolo"])
            $("#modal-text").html(object["corpo"])
            $("#modal-button").html("OK")

            $("#machine-modal").modal("toggle")
        }
    })
}

