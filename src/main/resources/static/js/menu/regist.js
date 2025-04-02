/**
 * Regist.js (menu)
 */
const ENDPOINTS = {
    tree: '/api/menus/tree',
}

const ELEMENTS = {
    liInfo: $('#menuTree').find('li').detach()
}

$(document).ready(function () {
    treeInit();
});

function treeInit() {

    fn_fetchGetData(ENDPOINTS.tree).then(data => {
        if ( data.result ) {
            if ( data.data != null ) {
                const buildTree = (list, parent) => {
                    list.forEach(item => {
                        const $node = ELEMENTS.liInfo.clone();
                
                        const $btn = $node.find('.menu-select-btn');
                        $btn.text(item.menuNm);
                        $btn.on('click', () => {
                            $('#parentSeq').val(item.menuSeq);
                            $('#parentSeqLabel').text(`${item.menuNm} (선택됨)`);
                            const modal = bootstrap.Modal.getInstance(document.getElementById('menuTreeModal'));
                            modal.hide();
                        });
                
                        if (item.children && item.children.length > 0) {
                            const $childUl = $node.find('.child-list');
                            buildTree(item.children, $childUl);
                        }
                
                        parent.append($node);
                    });
                };
              
                buildTree(data.data, $('#menuTree'));
            }
        } else {
            $('#alertModal').find('.modal-body').text( data.message );
            $('#alertModal').modal('show');
            $('#alertModal').on('hidden.bs.modal', function () {
                history.back();
            });
        }
    });
}