package com.oliinyk.yaroslav.easyreads.presentation.book.add_edit

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.util.ToastHelper
import com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.BookAddEditScreen
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//TODO: takePhotoLauncher implement or remove
private const val FILE_PROVIDER_AUTHORITY = "com.oliinyk.yaroslav.easyreads.fileprovider"

@AndroidEntryPoint
class BookEditFragment : Fragment() {

    private val args: BookEditFragmentArgs by navArgs()
    private val viewModel: BookAddEditViewModel by viewModels()

    //TODO: takePhotoLauncher implement or remove
    private var photoName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_in_from_bottom)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.removeUnusedCoverImage(requireContext().applicationContext)
            findNavController().popBackStack()
        }

        viewModel.updateStateUi {
            it.copy(book = args.book)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                EasyReadsTheme {
                    /*
                    BookAddEditScreen(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel,
                        onEvent = { event -> viewModel.onEvent(event) },
                        onClickCoverImage = {
                            launchBookCoverImagePicker()
                        },
                        onClickSave = {
                            viewModel.save(requireContext().applicationContext)
                            findNavController().popBackStack()
                        },
                        onClickCancel = {
                            findNavController().popBackStack()
                        }
                    )
                    */
                }
            }
        }
    }

    private val pickBookCoverImageLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewLifecycleOwner.lifecycleScope.launch {
            if (uri != null) {
                viewModel.updateCoverImage(requireContext().applicationContext, uri)
            }
        }
    }

    private fun launchBookCoverImagePicker() {
        val pickBookCoverImageIntent = pickBookCoverImageLauncher.contract.createIntent(
            requireContext(),
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
        if (canResolveIntent(pickBookCoverImageIntent)) {
            pickBookCoverImageLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            ToastHelper.show(
                requireContext(),
                getString(R.string.msg_warn__not_able_to_open_image_picker),
                Toast.LENGTH_LONG
            )
        }
    }

    //TODO: takePhotoLauncher implement or remove
    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto ->
        // Handle the result
        if (didTakePhoto && photoName != null) {
            viewModel.updateStateUi { stateUi ->
                stateUi.copy(tookPhotoName = photoName)
            }
        }
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    /*

    private var _binding: FragmentBookEditBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            getString(R.string.msg_error__cannot_access_binding)
        }

    private val args: BookEditFragmentArgs by navArgs()
    private val viewModel: BookEditViewModel by viewModels()
    private lateinit var _menuProvider: MenuProvider

    private var photoName: String? = null

    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto ->
        // Handle the result
        if (didTakePhoto && photoName != null) {
            viewModel.updateStateUi { stateUi ->
                stateUi.copy(tookPhotoName = photoName)
            }
        }
    }

    private val pickBookCoverImageLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewLifecycleOwner.lifecycleScope.launch {
            if (uri != null) {
                binding.coverImageText.visibility = View.GONE
                viewModel.updateCoverImage(requireContext().applicationContext, uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _menuProvider = createMenuProvider()

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_in_from_bottom)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.removeUnusedCoverImage(requireContext().applicationContext)
            findNavController().popBackStack()
        }

        viewModel.updateStateUi {
            it.copy(book = args.book)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        bindDoOnTextChanged()
//        bindTakePhotoLauncher(binding.coverImage)
        bindPickBookCoverImageLauncher(binding.coverImage)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateUi.collect { stateUi ->
                    updateUi(stateUi)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().addMenuProvider(_menuProvider)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().removeMenuProvider(_menuProvider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createMenuProvider(): MenuProvider {
        return object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_book_edit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_save_book -> {
                        viewModel.save(requireContext().applicationContext)
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }

        }
    }

    private fun setupUi() {
        binding.apply {
            bookShelves.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.stateUi.value.book?.let { book ->
                        viewModel.updateStateUi { oldStateUi ->
                            oldStateUi.copy(
                                book = book.copy(
                                    shelve = BookShelveType.valueOf(
                                        parent!!.getItemAtPosition(position)
                                            .toString()
                                            .uppercase()
                                            .replace(' ', '_')
                                    )
                                )
                            )
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    /* no-op */
                }
            }
        }
    }

    private fun updateUi(stateUi: StateUiBookEdit) {
        requireActivity().title = ""

        binding.apply {
            stateUi.book?.let { book ->
                if (title.text.toString() != book.title) {
                    title.setText(book.title)
                }
                if (author.text.toString() != book.author) {
                    author.setText(book.author)
                }
                if (pageAmount.text.toString() != book.pageAmount.toString() &&
                    book.pageAmount != 0
                ) {
                    pageAmount.setText(book.pageAmount.toString())
                }
                if (pageCurrent.text.toString() != book.pageCurrent.toString() &&
                    book.pageCurrent != 0
                ) {
                    pageCurrent.setText(book.pageCurrent.toString())
                }
                if (descriptionText.text.toString() != book.description) {
                    descriptionText.setText(book.description)
                }
                if (isbnInput.text.toString() != book.isbn) {
                    isbnInput.setText(book.isbn)
                }
                if (stateUi.pickedImageName != null) {
                    updateBookCoverImage(requireContext(), coverImage, stateUi.pickedImageName)
                } else if (stateUi.tookPhotoName != null) {
                    updateBookCoverImage(requireContext(), coverImage, stateUi.tookPhotoName)
                } else {
                    updateBookCoverImage(
                        requireContext(),
                        coverImage,
                        book.coverImageFileName
                    )
                }
                if (bookShelves.selectedItemPosition != book.shelve.ordinal) {
                    bookShelves.setSelection(book.shelve.ordinal)
                }
            }

            if (author.adapter == null && stateUi.authors.isNotEmpty()) {
                author.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        stateUi.authors)
                )
            }
        }
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    private fun bindPickBookCoverImageLauncher(view: View) {
        view.setOnClickListener {
            val pickBookCoverImageIntent = pickBookCoverImageLauncher.contract.createIntent(
                requireContext(),
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
            if (canResolveIntent(pickBookCoverImageIntent)) {
                pickBookCoverImageLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            } else {
                ToastHelper.show(
                    requireContext(),
                    getString(R.string.msg_warn__not_able_to_open_image_picker),
                    Toast.LENGTH_LONG
                )
            }
        }
    }

    private fun bindTakePhotoLauncher(view: View) {
        view.setOnClickListener {
            val captureImageIntent = takePhotoLauncher.contract.createIntent(
                requireContext(),
                Uri.parse("")
            )
            if (canResolveIntent(captureImageIntent)) {
                binding.coverImageText.visibility = View.INVISIBLE

                photoName = "IMG_${Date()}.JPG"
                val photoFile =
                    File(requireContext().applicationContext.filesDir, photoName ?: "")
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    FILE_PROVIDER_AUTHORITY,
                    photoFile
                )
                takePhotoLauncher.launch(photoUri)
            } else {
                ToastHelper.show(
                    requireContext(),
                    getString(R.string.msg_warn__not_able_to_launch_camera),
                    Toast.LENGTH_LONG
                )
            }
        }
    }

    private fun bindDoOnTextChanged() {
        binding.apply {
            title.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    viewModel.updateStateUi { stateUi ->
                        stateUi.copy(
                            book = stateUi.book?.copy(title = text.toString())
                        )
                    }
                }
            }
            author.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    viewModel.updateStateUi { stateUi ->
                        stateUi.copy(
                            book = stateUi.book?.copy(author = text.toString())
                        )
                    }
                }
            }
            pageAmount.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    if (!TextUtils.isEmpty(text) && TextUtils.isDigitsOnly(text)) {
                        viewModel.updateStateUi { stateUi ->
                            stateUi.copy(
                                book = stateUi.book?.copy(pageAmount = text.toString().toInt())
                            )
                        }
                    }
                }
            }
            pageCurrent.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    if (!TextUtils.isEmpty(text) && TextUtils.isDigitsOnly(text)) {
                        viewModel.updateStateUi { stateUi ->
                            stateUi.copy(
                                book = stateUi.book?.copy(pageCurrent = text.toString().toInt())
                            )
                        }
                    }
                }
            }
            descriptionText.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    viewModel.updateStateUi { stateUi ->
                        stateUi.copy(
                            book = stateUi.book?.copy(description = text.toString())
                        )
                    }
                }
            }
            isbnInput.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    viewModel.updateStateUi { stateUi ->
                        stateUi.copy(
                            book = stateUi.book?.copy(isbn = text.toString())
                        )
                    }
                }
            }
        }
    }

     */
}