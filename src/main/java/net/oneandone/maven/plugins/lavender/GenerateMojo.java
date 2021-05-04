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
import org.apache.maven.model.Scm;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.IOException;

/** Generates Lavender Properties */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PREPARE_PACKAGE, requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
public class GenerateMojo extends AbstractMojo {
    private final World world;

    /**
     * Directory where to place the Launch Script and the executable Jar file.
     * Usually, there's no need to change the default value, which is target.
     */
    @Parameter(defaultValue = "${project.build.directory}/${project.artifactId}/WEB-INF/lavender-next.properties")
    private FileNode dest;

    @Parameter(name = "name")
    private String name;

    @Parameter(name = "include")
    private String includes;

    @Parameter(name = "excludes")
    private String excludes;

    @Parameter(name = "resourcePathPrefix")
    private String resourcePathPrefix;

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
        Scm scm;

        scm = project.getScm();

        dest.writeLines(
                "scm." + name + "=" + scm.getConnection() + "\n",
                "scm." + name + ".devel=" + scm.getDeveloperConnection() + "\n",
                "scm." + name + ".path=src/main/webapp\n",
                "scm." + name + ".tag=" + scm.getTag() + "\n",
                "scm." + name + ".includes=" + includes + "\n",
                "scm." + name + ".excludes=" + excludes + "\n",
                "scm." + name + ".resourcePathPrefix" + resourcePathPrefix + "\n");
    }

    public void setDest(String dest) {
        this.dest = world.file(dest);
    }

    public Node getDest() {
        return dest;
    }
}
