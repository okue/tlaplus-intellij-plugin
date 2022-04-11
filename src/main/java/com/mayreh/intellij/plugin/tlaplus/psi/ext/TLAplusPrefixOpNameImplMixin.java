package com.mayreh.intellij.plugin.tlaplus.psi.ext;

import java.util.Objects;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.mayreh.intellij.plugin.tlaplus.psi.TLAplusNameFixness;
import com.mayreh.intellij.plugin.tlaplus.psi.TLAplusPrefixOpName;

public abstract class TLAplusPrefixOpNameImplMixin extends TLAplusNamedElementImplBase implements TLAplusPrefixOpName {
    protected TLAplusPrefixOpNameImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return Stream.of(getPrefixOp44(),
                         getPrefixOp415(),
                         getPrefixOp88(),
                         getPrefixOp99(),
                         getPrefixOp1212()
                     ).filter(Objects::nonNull)
                     .findFirst()
                     .orElse(null);
    }

    @Override
    public PsiElement setName(@NotNull String name) {
        throw new IncorrectOperationException("Can't rename symbolic operator");
    }

    @Override
    public @NotNull TLAplusNameFixness fixness() {
        return TLAplusNameFixness.PREFIX;
    }
}
