import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.EditorModificationUtil

// Действие для вставки предустановленного текста в редактор
class InsertTextAction : AnAction() {

    // Логгер для отслеживания действий и отладки
    private val logger = Logger.getInstance(InsertTextAction::class.java)

    /**
     * Выполняет действие, когда оно активируется (например, через меню или горячую клавишу).
     * Этот метод получает текущий редактор и вставляет предустановленный текст в позицию каретки.
     */
    override fun actionPerformed(e: AnActionEvent) {
        // Логируем событие активации действия
        logger.info("Действие активировано!")

        // Получаем активный редактор из события действия
        val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR)

        // Проверяем, что редактор доступен (не равен null)
        if (editor != null) {
            // Выполняем вставку текста в рамках команды для обеспечения возможности отмены
            WriteCommandAction.runWriteCommandAction(editor.project) {
                // Вставляем предустановленный текст (сообщение о копирайте) в редактор
                insertText(editor, "\n/*\n©ITMO.\n\nIT's MOre than a university!\n*/")
            }
        } else {
            // Логируем ошибку, если редактор не найден (например, нет активного редактора)
            logger.error("Редактор не найден! Невозможно выполнить действие.")
        }
    }

    /**
     * Вставляет указанный текст в позицию каретки редактора.
     *
     * @param editor Редактор, в который нужно вставить текст.
     * @param text Текст для вставки.
     */
    private fun insertText(editor: Editor, text: String) {
        // Используем EditorModificationUtil для вставки текста в позицию каретки
        // Параметры 'true' означают, что:
        // 1. Каретка будет перемещена в конец вставленного текста.
        // 2. Текст будет вставлен даже если редактор находится в режиме только для чтения (если это возможно).
        EditorModificationUtil.insertStringAtCaret(editor, text, true, true)
    }
}
