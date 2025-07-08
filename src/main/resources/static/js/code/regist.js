/**
 * Regist.js (code)
 */

const ENDPOINTS = {
    check: '/api/code/check-duplicate',
    save: '/api/code',
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