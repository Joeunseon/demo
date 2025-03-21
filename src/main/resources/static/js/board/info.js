/**
 * Info.js (board)
 */
const ENDPOINTS = {
    api: '/api/board/',
    list: '/board/list'
};

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const boardSeq = $('#boardSeq').val();
    if ( boardSeq ) {
        fn_fetchGetData(`${ENDPOINTS.api}${boardSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data.data != null ) {
                    const result = data.data.data;
                    fileInfo(result.fileSeq);
                    $('.title').text(result.title);
                    $('.writerNm').text(result.writerNm);
                    $('.regDt').text(result.regDt);
                    $('.viewCnt').text(result.viewCnt);
                    ui.toastViewer(result.content);
                }

                if ( !data.data.editable ) {
                    $('.editable').remove();
                }
            } else {
                $('#alertModal').find('.modal-body').text($('#errorRequest').val());
                $('#alertModal').modal('show');
                $('#alertModal').on('hidden.bs.modal', function () {
                    history.back();
                });
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

function deleteConfirm() {
    $('#confirmModal .modal-body').html($('#deleteConfirm').val());
    $('#confirmModal .saveBtn').off('click').on('click', softDelete);
    $('#confirmModal').modal('show');
}

function softDelete() {
    const boardSeq = $('#boardSeq').val();

    fn_fetchDeleteData(`${ENDPOINTS.api}${boardSeq}`).then(data => {
        if ( data.result ) {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            $('#alertModal').on('hidden.bs.modal', function () {
                location.href = `${ENDPOINTS.list}`;
            });
        }
    });
}