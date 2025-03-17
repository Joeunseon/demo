/**
 * Info.js (board)
 */
const ENDPOINTS = {
    info: '/api/board/'
}

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const boardSeq = $('#boardSeq').val();
    if ( boardSeq ) {
        fn_fetchGetData(`${ENDPOINTS.info}${boardSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.title').text(result.title);
                    $('.writerNm').text(result.writerNm);
                    $('.regDt').text(result.regDt);
                    $('.viewCnt').text(result.viewCnt);
                    ui.toastViewer(result.content);
                } else {
                    $('#alertModal').find('.modal-body').text($('#errorRequest').val());
                    $('#alertModal').modal('show');
                    $('#alertModal').on('hidden.bs.modal', function () {
                        history.back();
                    });
                }
            }
        });
    } else {
        $('#alertModal').find('.modal-body').text($('#errorRequest').val());
        $('#alertModal').modal('show');
        $('#alertModal').on('hidden.bs.modal', function () {
            history.back();
        });
    }
}