const prayers = [
    "fajr",
    "dhuhr",
    "asr",
    "maghrib",
    "isha"
];

function updateCount() {

    let completed = 0;

    prayers.forEach(id => {

        if(document.getElementById(id).checked){
            completed++;
        }

    });

    document.getElementById("count").textContent =
        completed;
}

prayers.forEach(id => {

    document
        .getElementById(id)
        .addEventListener(
            "change",
            updateCount
        );

});

function savePrayer(){

    const data = {
        fajr: document.getElementById("fajr").checked,
        dhuhr: document.getElementById("dhuhr").checked,
        asr: document.getElementById("asr").checked,
        maghrib: document.getElementById("maghrib").checked,
        isha: document.getElementById("isha").checked
    };

    if(window.Android){
        Android.savePrayer(
            JSON.stringify(data)
        );
    }

    alert("Saved");
}

function drawDummyChart(){

    const canvas =
        document.getElementById("chart");

    const ctx =
        canvas.getContext("2d");

    const values =
        [5,4,3,5,4,5,2];

    const width = 30;

    values.forEach((v,i)=>{

        ctx.fillStyle =
            "#2E7D32";

        ctx.fillRect(
            i*40 + 20,
            160 - (v*25),
            width,
            v*25
        );
    });
}

drawDummyChart();
