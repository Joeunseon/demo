/**
 * Login.js 
 */

$(document).ready(function () {
    const errorMsg = $('#errorMsg').val();

    if ( errorMsg && errorMsg.trime !== '' ) {

        $('#alertModal .modal-body').text(errorMsg);
        $('#alertModal').modal('show');
    } 
});