
 //Memoryspel
var Memory = {
    array: [],
    countPair: 0,
    countTry: 0,
    init: function(){
        slumpArray = RandomGenerator.getPictureArray(4, 4);
        Memory.generateMemoryBoard();
    },
    generateMemoryBoard: function(){
        var skapaDivTag = document.getElementById("container");
        var tabellen = document.createElement("table");
        skapaDivTag.appendChild(tabellen);
        var brickan = 0;
        for (var rad = 0; rad < 4; rad++) {
            var tableRow = document.createElement("tr");
            tabellen.appendChild(tableRow);
            for (var col = 0; col < 4; col++) {
                var tableData = document.createElement("td");
                tableRow.appendChild(tableData);
                var pics = document.createElement("img");
                pics.setAttribute("src", "pics/0.png");
                var linkPics = document.createElement("a");
                linkPics.setAttribute("href", "#");
                linkPics.appendChild(pics);
                tableData.appendChild(linkPics);
                Memory.turnBrick(brickan, linkPics);
                brickan++;
            }
        }
    },
    turnBrick: function(brickan, link){ 
        link.onclick = function(){
            if (this.getElementsByTagName("img")[0].getAttribute("src") !== "pics/0.png") {
                return false;
            }
            Memory.array.push(link);
            if (Memory.array.length === 2 || Memory.array.length === 1) {
                this.getElementsByTagName("img")[0].setAttribute("src", "pics/" + slumpArray[brickan] + ".png");
                if (Memory.array.length === 2) {
                    setTimeout(function(){
                        Memory.checkBrick(Memory.array);
                    }, 900);
                }
            }
        };
    },
    checkBrick: function(slumpArray){
        if (slumpArray[0].getElementsByTagName("img")[0].getAttribute("src") === slumpArray[1].getElementsByTagName("img")[0].getAttribute("src")) {
            Memory.countPair++;
            if (Memory.countPair === 8) {
                alert("Grattis! \nNi klararde det på " + Memory.countTry + " försök");
                var confa = confirm("Vill Ni spela igen?");
                if (confa === true) {
                    location.reload(true);
                }
                else {
                    alert("Jaja!\nNi har säkert bättre saker att göra");
                }
            }
            Memory.array = [];
        }
        else {
            slumpArray[0].getElementsByTagName("img")[0].setAttribute("src", "pics/0.png");
            slumpArray[1].getElementsByTagName("img")[0].setAttribute("src", "pics/0.png");
            Memory.array = [];
        }
        Memory.countTry++;
    }
};

window.onload = Memory.init;

