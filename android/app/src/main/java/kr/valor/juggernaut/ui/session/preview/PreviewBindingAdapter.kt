package kr.valor.juggernaut.ui.session.preview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.session.getSessionRoutineItems

@BindingAdapter("previewRoutineItems")
fun RecyclerView.bindPreviewState(uiState: PreviewUiState) {
    val previewFooterButtonText = context.getString(R.string.session_footer_button_text_preview)
    when(uiState) {
        is PreviewUiState.Loading -> return
        is PreviewUiState.Result -> {
            val session = uiState.session
            val sessionRoutineItems = session.getSessionRoutineItems(footerButtonText = previewFooterButtonText)
            val adapter = adapter as PreviewAdapter
            adapter.submitList(sessionRoutineItems)
        }
    }
}