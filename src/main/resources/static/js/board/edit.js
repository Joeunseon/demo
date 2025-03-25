/**
 * Edit.js (board)
 */
const ENDPOINTS = {
    api: '/api/board/',
    info: '/board/info',
    edit: '/api/board'
};
const form = document.getElementById('boardForm');

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
                    $('#title').val(result.title);
                    
                    let editor = $('.toast-editor').data('toastEditor');
                    editor.setHTML(result.content, true);

                    if ( result.fileSeq ) {
                        fileInit(result.fileSeq);
                        $('#fileSeq').val(result.fileSeq);
                    }
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

function boardValidity() {
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    let editor = $('.toast-editor').data('toastEditor');
    let content = editor.getMarkdown().trim();
    if ( !content ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', '내용'));
        $('#alertModal').modal('show');
        return;
    } else {
        if ( editor.isWysiwygMode() )
            content = editor.getHTML().trim();

        $('#content').val(content);
    }

    boardConfrim();
}

function boardConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', function () {
            uploadFiles(board);
        });
        $('#confirmModal').modal('show');
    } 
}

function board() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPatchData(`${ENDPOINTS.edit}`, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    location.href = `${ENDPOINTS.info}?boardSeq=${data.data}`;
                });
            }
        });
}