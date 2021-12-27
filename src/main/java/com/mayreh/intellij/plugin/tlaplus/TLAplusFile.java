package com.mayreh.intellij.plugin.tlaplus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.Key;
import com.intellij.psi.FileViewProvider;
import com.mayreh.intellij.plugin.tlaplus.fragment.TLAplusFragmentFile.CodeFragmentContext;

public class TLAplusFile extends PsiFileBase {
    private static final Key<CodeFragmentContext> CONTEXT_KEY =
            Key.create("TLA.code.fragment.context");

    public TLAplusFile(@NotNull FileViewProvider viewProvider,
                       @NotNull Language language) {
        super(viewProvider, language);
    }

    public void setCodeFragmentContext(@NotNull CodeFragmentContext context) {
        putCopyableUserData(CONTEXT_KEY, context);
    }

    public @Nullable CodeFragmentContext codeFragmentContext() {
        return getCopyableUserData(CONTEXT_KEY);
    }

    @Override
    public @NotNull FileType getFileType() {
        return TLAplusFileType.INSTANCE;
    }
}
