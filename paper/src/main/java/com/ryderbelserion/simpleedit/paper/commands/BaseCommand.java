package com.ryderbelserion.simpleedit.paper.commands;

import com.ryderbelserion.fusion.api.files.FileManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import dev.triumphteam.cmd.core.annotations.Command;

@Command(value = "simpleedit")
public abstract class BaseCommand {

    protected final SimpleEdit plugin = SimpleEdit.getPlugin();

    protected final FileManager fileManager = this.plugin.getFileManager();

    protected final UserManager userManager = this.plugin.getUserManager();

    protected final FusionPaper fusion = this.plugin.getFusion();

}