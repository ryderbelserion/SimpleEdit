package com.ryderbelserion.simpleedit.paper.loader;

import com.ryderbelserion.simpleedit.paper.api.enums.Plugins;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.repository.RemoteRepository;

public class SimpleLoader implements PluginLoader {

    @Override
    public void classloader(PluginClasspathBuilder builder) {
        final MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public").build());

        resolver.addRepository(new RemoteRepository.Builder("crazycrewReleases", "default", "https://repo.crazycrew.us/releases/").build());

        resolver.addDependency(Plugins.fusion.asDependency());

        builder.addLibrary(resolver);
    }
}