/**
 * Edit.js (role)
 */

const ENDPOINTS = {
    check: '/api/role/check-duplicate',
    save: '/api/role',
    info: '/role/info'
}

const form = document.getElementById('roleForm');

$(document).ready(function () {

    infoInit();

    $('#roleNm').on('focusout', function () {
        $('#checkBtn').data('flag', false);
    });
});

function infoInit() {
    const roleSeq = $('#roleSeq').val();
    if ( roleSeq ) {
        fn_fetchGetData(`${ENDPOINTS.save}/${roleSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('#roleNm').val(result.roleNm);
                    $('#roleDesc').val(result.roleDesc);
                    $('#checkBtn').data('flag', true);
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

function checkDuplicate() {
    const id = 'roleNm';
    if ( !$('#'+id).val() ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', $(`label[for=${id}]`).text()));
        $('#alertModal').modal('show');
        return;
    }
        
    fn_fetchGetData(`${ENDPOINTS.check}?${id}=${$('#'+id).val()}&roleSeq=${$('#roleSeq').val()}`)
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

    fn_fetchPatchData(ENDPOINTS.save, data)
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