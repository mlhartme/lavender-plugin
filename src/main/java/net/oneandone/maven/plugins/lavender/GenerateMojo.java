/*
 * Copyright 1&1 Internet AG, https://github.com/1and1/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oneandone.maven.plugins.lavender;

import net.oneandone.sushi.fs.Node;
import net.oneandone.sushi.fs.World;
import net.oneandone.sushi.fs.file.FileNode;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.IOException;

/**
 * Generates an application file. Merges dependency jars into a single file, prepended with a launch shell script.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.RUNTIME,
      threadSafe = true)
public class GenerateMojo extends AbstractMojo {
    protected final World world;

    /**
     * Directory where to place the Launch Script and the executable Jar file.
     * Usually, there's no need to change the default value, which is target.
     */
    @Parameter(defaultValue = "${project.build.directory}")
    protected FileNode dir;

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    public GenerateMojo() throws IOException {
        this.world = World.create();
    }

    public void execute() throws MojoExecutionException {
        try {
            doExecute();
        } catch (IOException e) {
            throw new MojoExecutionException("cannot generate application: " + e.getMessage(), e);
        }
    }

    public void doExecute() throws IOException {
        getLog().info("TODO");
    }

    public void setDir(String dir) {
        this.dir = world.file(dir);
    }

    public Node getDir() {
        return dir;
    }
}
