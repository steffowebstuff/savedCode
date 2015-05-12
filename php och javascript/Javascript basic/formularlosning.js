
 //Javascriptklass för validering av formulär
var validering = {
    init: function () {
        var form = document.getElementById("formularet");
        var knappSatan = document.getElementById("button");
        var field = document.getElementById("field");
        field.removeChild(knappSatan);
        var linkKnapp = document.createElement("a");
        linkKnapp.setAttribute("href", "#");
        var skickaText = document.createTextNode("Skicka uppgifter!");
        field.appendChild(linkKnapp);
        linkKnapp.appendChild(skickaText);
        var firstName = document.getElementById("firstname");
        var lastName = document.getElementById("lastname");
        var postNumber = document.getElementById("postnumber");
        var phoneNumber = document.getElementById("phonenumber");
        var eMail = document.getElementById("mail");

        firstName.onfocus = function () {
            var divTag = document.createElement("div");
            divTag.className = "tooltip";
            document.body.appendChild(divTag);
            var text = document.createTextNode("Endast bokstäver");
            divTag.appendChild(text);
			var pos = validering.findPos(this);
            divTag.style.left = pos[0] + 195 + "px";
            divTag.style.top = pos[1] + "px";
            firstName.onblur = function () {
                document.body.removeChild(divTag);

            };

        };
        lastName.onfocus = function () {
            var divTag = document.createElement("div");
            divTag.className = "tooltip";
            document.body.appendChild(divTag);
            var text = document.createTextNode("Endast bokstäver");
            divTag.appendChild(text);
            var pos = validering.findPos(this);
            divTag.style.left = pos[0] + 195 + "px";
            divTag.style.top = pos[1] + "px";
            lastName.onblur = function () {
                document.body.removeChild(divTag);
            };
        };
        postNumber.onfocus = function () {
            var divTag = document.createElement("div");
            divTag.className = "tooltip";
            document.body.appendChild(divTag);
            var text = document.createTextNode("Enligt formen XXX-XX");
            divTag.appendChild(text);
            var pos = validering.findPos(this);
            divTag.style.left = pos[0] + 195 + "px";
			divTag.style.top = pos[1] + "px";
            postNumber.onblur = function () {
                document.body.removeChild(divTag);
            };
        };
        phoneNumber.onfocus = function () {
            var divTag = document.createElement("div");
            divTag.className = "tooltip";
            document.body.appendChild(divTag);
            var text = document.createTextNode("Endast siffror");
            divTag.appendChild(text);
            var pos = validering.findPos(this);
            var width = this.clientWidth;
            divTag.style.left = pos[0] + width+ "px";
            divTag.style.top = pos[1] + "px";
            phoneNumber.onblur = function () {
                document.body.removeChild(divTag);
            };
        };
        eMail.onfocus = function () {
            var divTag = document.createElement("div");
            divTag.className = "tooltip";
            document.body.appendChild(divTag);
			var text = document.createTextNode("Ange en äkta e-postadress");
            divTag.appendChild(text);
            var pos = validering.findPos(this);
            divTag.style.left = pos[0] + 195 + "px";
            divTag.style.top = pos[1] + "px";
            eMail.onblur = function () {
                document.body.removeChild(divTag);
            };
        };
        firstName.onchange = function () {
            if (!form.elements["firstname"].value.match(/^[a-zåäö]+$/i)) {
                form.elements["firstname"].className = "error";
                form.elements["firstname"].select();
                return false;
            }
            else {
                form.elements["firstname"].className = "correct";
            }
        };
        lastName.onchange = function () {
            if (!form.elements["lastname"].value.match(/^[a-zåäö]+$/i)) {
                form.elements["lastname"].className = "error";
                form.elements["lastname"].select();
                return false;

            }

            else {

                form.elements["lastname"].className = "correct";

            }

        };

        postNumber.onchange = function () {
            if (!form.elements["postnumber"].value.match(/^[0-9]{3}-[0-9]{2}$/)) {
                form.elements["postnumber"].className = "error";
                form.elements["postnumber"].select();
                return false;
            }
            else {
                form.elements["postnumber"].className = "correct";
            }
        };
        phoneNumber.onchange = function () {
            if (!form.elements["phonenumber"].value.match(/^[0-9]+$/)) {
                form.elements["phonenumber"].className = "error";
                form.elements["phonenumber"].select();
                return false;
            }
            else {
                form.elements["phonenumber"].className = "correct";
            }
        };
        eMail.onchange = function () {
            if (!form.elements["mail"].value.match(/^[\w]+(\.[\w]+)*@([\w]+\.)+[a-z]{2,7}$/i)) {
                form.elements["mail"].className = "error";
                form.elements["mail"].select();
                return false;
            }
            else {
                form.elements["mail"].className = "correct";
            }
        };
        linkKnapp.onclick = function () {
            if (!form.elements["firstname"].value.match(/^[a-zåäö]+$/i)) {
                alert("Fyll i korrekt förnamn!");
                return false;
            }
            if (!form.elements["lastname"].value.match(/^[a-zåäö]+$/i)) {
                alert("Fyll i korrekt efternamn!");
                return false;
            }
            if (!form.elements["postnumber"].value.match(/^[0-9]{3}-[0-9]{2}$/)) {
                alert("Fyll i korrekt postnummer!");
                return false;
            }
            if (!form.elements["phonenumber"].value.match(/^[0-9]+$/)) {
                alert("Fyll i korrekt telefonnummer!");
                return false;
            }
            if (!form.elements["mail"].value.match(/^[\w]+(\.[\w]+)*@([\w]+\.)+[a-z]{2,7}$/i)) {
                alert("Fyll i giltig e-postadress!");
                return false;
            }
            var bigDiv = document.createElement("div");
            bigDiv.className = "stordiv";
            var newDiv = document.createElement("div");
            newDiv.className = "popupwindow";
            document.body.appendChild(bigDiv);
            document.body.appendChild(newDiv);
            // skapar element och hänger på värdena
            var textHeader = document.createTextNode("Kontrollera era uppgifter:");
            newDiv.appendChild(textHeader);
            var textFirstName = document.createTextNode(form.elements["firstname"].name + " : " + form.elements["firstname"].value);
            var textP = document.createElement("p");
            textP.appendChild(textFirstName);
            newDiv.appendChild(textP);
            var textLastName = document.createTextNode(form.elements["lastname"].name + " : " + form.elements["lastname"].value);
            textP = document.createElement("p");
            textP.appendChild(textLastName);
            newDiv.appendChild(textP);

            var textPostNumber = document.createTextNode(form.elements["postnumber"].name + " : " + form.elements["postnumber"].value);
            textP = document.createElement("p");
            textP.appendChild(textPostNumber);
            newDiv.appendChild(textP);
            var textPhoneNumber = document.createTextNode(form.elements["phonenumber"].name + " : " + form.elements["phonenumber"].value);
            textP = document.createElement("p");
            textP.appendChild(textPhoneNumber);
            newDiv.appendChild(textP);

            var textMail = document.createTextNode(form.elements["mail"].name + " : " + form.elements["mail"].value);
            textP = document.createElement("p");
            textP.appendChild(textMail);
            newDiv.appendChild(textP);
            var knappSkicka = document.createElement("input");
            knappSkicka.type = "submit";
            knappSkicka.value = "Gå vidare!";
            knappSkicka.className = "inputKnapp";
            newDiv.appendChild(knappSkicka);

            // "Disabla" fälten
            form.elements["firstname"].disabled = true;
            form.elements["lastname"].disabled = true;
            form.elements["postnumber"].disabled = true;
            form.elements["phonenumber"].disabled = true;
            form.elements["mail"].disabled = true;
            field.removeChild(linkKnapp);   

			// vid klick på skickaknappen skickas datan
            knappSkicka.onclick = function () {    
            // Göra fälten ändringsbara igen (annars skickas inte datan)
                form.elements["firstname"].disabled = false;
                form.elements["lastname"].disabled = false;
                form.elements["postnumber"].disabled = false;
                form.elements["phonenumber"].disabled = false;
                form.elements["mail"].disabled = false;
                    field.appendChild(linkKnapp);
                    linkKnapp.appendChild(skickaText);
                    form.submit();              
            };

            var knappTabort = document.createElement("input");
            knappTabort.type = "button";
            knappTabort.value = "Ändra uppgifter!";
            knappTabort.className = "inputKnapp";

			// ändra uppgifter tar bort "divvarna"
            knappTabort.onclick = function () {
             // Göra fälten ändringsbara igen
                form.elements["firstname"].disabled = false;
                form.elements["lastname"].disabled = false;
                form.elements["postnumber"].disabled = false;
                form.elements["phonenumber"].disabled = false;
                form.elements["mail"].disabled = false;
                field.appendChild(linkKnapp);
                linkKnapp.appendChild(skickaText);
                document.body.removeChild(newDiv);
                document.body.removeChild(bigDiv);          
            };
            newDiv.appendChild(knappTabort);
        };
    },
    findPos: function (obj) {
        var curleft = curtop = 0;
        if (obj.offsetParent) {
            do {
                curleft += obj.offsetLeft;
                curtop += obj.offsetTop;
            }
            while (obj = obj.offsetParent);
            return [curleft, curtop];
        }
    }
};
window.onload = validering.init;
