/**
 * Regist.js (code)
 */

const ENDPOINTS = {
    check: '/api/code/check-duplicate',
    saveGRP: '/api/code-group',
    saveCD: '/api/code',
    info: '/code/info'
}

const form = document.getElementById('codeForm');

$(document).ready(function () {
    $('input[name=codeType]').on('change', function() {
        const codeType = $('input[name=codeType]:checked').val();

        if ( codeType == 'grp' ) {
            $('#grpSeq').closest('div').hide();
        } else {
            $('#grpSeq').closest('div').show();
        }
    });

    $('#cd').on('focusout', function () {
        $('#checkBtn1').data('flag', false);
    });

    $('#cdNm').on('focusout', function () {
        $('#checkBtn2').data('flag', false);
    });
});

function checkDuplicate(id, el) {

    if ( !$('#'+id).val() ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', $(`label[for=${id}]`).text()));
        $('#alertModal').modal('show');
        return;
    }

    const btn = $(el).attr('id');
        
    fn_fetchGetData(`${ENDPOINTS.check}?${id}=${$('#'+id).val()}&codeType=${$('input[name=codeType]:checked').val()}`)
        .then(data => {
            $('#alertModal .modal-body').text(data.message);
            $('#alertModal').modal('show');

            $('#'+btn).data('flag', data.result);
        });
}

function codeValidity() {

    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    if ( !$('#checkBtn1').data('flag') ) {
        $('#alertModal').find('.modal-body').text($('#duplication').val().replace('{0}', '코드'));
        $('#alertModal').modal('show');
        return;
    }

    if ( !$('#checkBtn2').data('flag') ) {
        $('#alertModal').find('.modal-body').text($('#duplication').val().replace('{0}', '코드 이름'));
        $('#alertModal').modal('show');
        return;
    }

    codeConfrim();
}

function codeConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', code);
        $('#confirmModal').modal('show');
    }
}

function code() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    const codeType = $('input[name=codeType]:checked').val();
    const saveUrl = ENDPOINTS['save' + codeType];

    fn_fetchPostData(saveUrl, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    location.href = `${ENDPOINTS.info}?${codeType.toLowerCase()}Seq=${data.data}`;
                });
            }
        });
}