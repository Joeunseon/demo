/**
 * Regist.js (role)
 */
const ENDPOINTS = {
    check: '/api/role/check-duplicate',
    save: '/api/role',
    info: '/role/info'
}

const form = document.getElementById('roleForm');

$(document).ready(function () {
    $('#roleNm').on('focusout', function () {
        $('#checkBtn').data('flag', false);
    });
});

function checkDuplicate() {
    const id = 'roleNm';
    if ( !$('#'+id).val() ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', $(`label[for=${id}]`).text()));
        $('#alertModal').modal('show');
        return;
    }
        
    fn_fetchGetData(`${ENDPOINTS.check}?${id}=${$('#'+id).val()}`)
        .then(data => {
            $('#alertModal .modal-body').text(data.message);
            $('#alertModal').modal('show');

            $('#checkBtn').data('flag', data.result);
        });
}

function roleValidity() {

    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    if ( !$('#checkBtn').data('flag') ) {
        $('#alertModal').find('.modal-body').text($('#duplication').val().replace('{0}', '권한 이름'));
        $('#alertModal').modal('show');
        return;
    }

    roleConfrim();
}

function roleConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', role);
        $('#confirmModal').modal('show');
    }
}

function role() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(ENDPOINTS.save, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    location.href = `${ENDPOINTS.info}?roleSeq=${data.data}`;
                });
            }
        });
}