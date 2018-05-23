$(function () {

    $("#pop").on("click", function () {
        $('#imagepreview').attr('src', $('#imageresource').attr('src'));
        $('#myModal').modal('show');
    });

    //http://momentjs.com
    //общий опыт
    moment().locale('ru');
    var experiences = getMoment($("#experiences").data("start")).fromNow(true);
    $("#experiences").text('Опыт работы :' + experiences);

    //опыт по каждой строке отдельно
    $("div.experiencesrow").each(function () {

        var start = getMoment($(this).data("start"));
        var end = getMoment($(this).data("end")).endOf('months');

        result = getPeriod(start, end) + "<div>" + end.from(start, true) + "</div>";

        $(this).html(result);

    })
})

function getPeriod(start, end) {

    var result = start.format("MMMM YYYY") + " - ";

    result += (end.isSame(moment().endOf('months'))) ? " по настоящее время" : end.format("MMMM YYYY");

    result = result.substring(0, 1).toUpperCase() + result.substring(1);

    return result;
}

function getMoment(text) {

    var result = moment(text, "YYYY-MM");

    if (!result.isValid()) {
        return moment();
    }

    return result;
}