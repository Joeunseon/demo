/**
 * Regist.js (file)
 */

$(document).ready(function () {

    Dropzone.autoDiscover = false;

    let dropzoneElement = document.querySelector('.dropzone');

    if ( dropzoneElement ) {
        if (dropzoneElement.dropzone) {
            dropzoneElement.dropzone.destroy();
        }

        new Dropzone(dropzoneElement, {
            url: '/api/file', // 업로드 서버 URL
            method: 'post',
            autoProcessQueue: false, // 자동 업로드 여부
            clickable: true, // 클릭 기능 여부
            autoQueue: false, // 드래그 드랍 후 바로 서버 전송 여부
            uploadMultiple: true, // 다중 업로드 여부
            addRemoveLinks: true, // 파일 삭제 버튼
            dictRemoveFile: '삭제', // 파일 삭제 버튼 표시 텍스트
            init: function () {
                let oThis = this;
        
                $('#fileBtn').on('click', function (e) {
                    e.preventDefault();
                    dropzoneElement.click();
                });
            }
        });
    }

});