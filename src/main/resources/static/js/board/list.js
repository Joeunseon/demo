/**
 * List.js (board)
 */

const elements = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

$(document).ready(function () {
    listInit();
});

function listInit() {
    fn_resultList('searchForm', drawResultList);
}

function drawResultList(data) {
    $('.listFragment').find('tbody').empty();
    $('.listFragment').find('.totalCnt').text(data.totalCnt);

    if ( data.totalCnt > 0 ) {
        data.resultList.forEach(info => {
            let infoClone =  elements.trInfo.clone();
            infoClone.find('.rownum').text(info.row);
            infoClone.find('.title').text(info.title);
            infoClone.find('.writerNm').text(info.writerNm);
            infoClone.find('.regDt').text(info.regDt);
            infoClone.find('.viewCnt').text(info.viewCnt);

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(elements.trNone.clone());
    }
}