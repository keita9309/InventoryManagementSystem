
let multiDeleteBtn = document.querySelector('#multi-delete-btn');
// console.log(multiDeleteBtn);
multiDeleteBtn.disabled = true;

// 初期表示で「削除(※)ボタン」を非活性にする
//window.addEventListener('load', function() {
//  multiDeleteBtn.disabled = true;
//})

// チェックボックスが押下された時に動く処理
function countChecks() {
    // チェックボックスの要素を取得する
    const el = document.getElementsByClassName("checks");

    let count = 0;
    // チェクボックスの数分ループ
    for (let i = 0; i < el.length; i++) {
        // チェックされている数をカウント
        if (el[i].checked) {
            count++;
        }
    }
    // チェック済みの項目が2個以上なら、削除(※)ボタンを活性にする
    if(count >= 2) {
        multiDeleteBtn.disabled = false;
    } else {
        multiDeleteBtn.disabled = true;
    }
};


function submitForm() {
    // チェックボックスの要素を取得する
    let el = document.getElementsByClassName("checks");
    let selectedRecords = [];

    for (let i = 0; i < el.length; i++) {
            // チェックされている数をカウント
            if (el[i].checked) {
                selectedRecords.push(el[i].value);
            }
    }

    // 値を渡すinput要素を取得
    let inputElement = document.querySelector('#selectedRecords');
    // value属性に値を設定
    inputElement.setAttribute("value", selectedRecords);
    // フォームを送信
    document.querySelector('#delete-form').submit();

}