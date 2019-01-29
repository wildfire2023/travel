/**
 * 前端md5散列防止数据盗用
 * @param string
 * @returns {*}
 */
function encode(string) {
    return hex_md5(string)
}
