/**
 * Regist.js (Board)
 */

const ENDPOINTS = {
    save: '/api/board',
    info: '/board/info'
};
const form = document.getElementById('boardForm');

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

    fn_fetchPostData(ENDPOINTS.save, data)
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