/**
 * Created by Roger on 21/03/2015.
 * Dynamic Breadcrumbs for Bootstrap
 */

$(document).ready(function() {
    /**
     * Script for dynamic breadcrumbs
     */
    var crumb = [];

    $(".dynamic-crumbs li a").each(function() {
        crumb.push("<li><a href=\"#\" id=\"" + $(this).attr('id') + "\">" + $(this).text() + "</a></li>")
    });

    var amountCrumb = crumb.length - 1;
    var crumbs = [];

    for (var x = amountCrumb; x > -1; x--) {
        crumbs[x] = crumb.pop();
    }

    $(".dynamic-crumbs").empty();
    $(".dynamic-crumbs").append(crumbs[0]);

    var count = 1;
    var stack = [];

    function addCrumb() {
        if (count < amountCrumb+1 && count > 0) {
            $("ol").append(crumbs[count]);
            stack.push("crumb" + count);
            count++;
        }
    }

    function removeCrumb() {
        if (count < amountCrumb+2 && count > 1) {
            var top = stack.pop();
            $("#"+ top).closest("li").remove();
            count--;
        }
    }

    $(".increase").click(function() {
        addCrumb();
    });

    $(".decrease").click(function() {
        removeCrumb();
    });

    /**
     * Multi-steps form, ignore this
     */
    $('.next').on('click', function() {
        var current = $(this).data('currentBlock');
        var next = $(this).data('nextBlock');

        $('.block' + current)
            .removeClass('show')
            .addClass('hidden');

        $('.block' + next)
            .removeClass('hidden')
            .addClass('show');
    });
});
