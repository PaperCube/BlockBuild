package studio.papercube.blockbuild.levelsupport.presetUtilities;

import studio.papercube.blockbuild.edgesupport.binlevel.Level;
import studio.papercube.blockbuild.edgesupport.binlevel.LevelReader;
import studio.papercube.blockbuild.edgesupport.binlevel.LevelWriter;

import java.io.File;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class LevelModifier {
    private Stream<Pair<File, Level>> levelStream;
    private Function<String, String> levelNameModifier = any -> any;

    public LevelModifier(String levelsPath) {
        this(new File(levelsPath));
    }

    public LevelModifier(File levelDirectory) {
        levelStream = Stream.of(levelDirectory.listFiles())
                .filter(file -> file.getAbsolutePath().endsWith(".bin"))
                .map(f -> new Pair<>(f, new LevelReader(f).read()));
    }

    public LevelModifier operate(final UnaryOperator<Level> operation) {
        levelStream = levelStream.map(fileLevelPair -> new Pair<>(fileLevelPair.getFirst(), operation.apply(fileLevelPair.getSecond())));
        return this;
    }


    /**
     * 对最后生成的文件名进行修改。<br/>
     * 如果这是一个费时操作，那么它将会体现在{@link LevelModifier#output(java.io.File)}方法的执行上
     *
     * @param levelNameModifier 原文件名->修改过的文件名
     * @return this. 链式调用
     */
    public LevelModifier modifyFileName(Function<String, String> levelNameModifier) {
        this.levelNameModifier = levelNameModifier;
        return this;
    }

    public void output(File directory) {
        levelStream.forEach(pair -> {
            final File originalFile = pair.getFirst();
            final Level modifiedLevel = pair.getSecond();
            final String path = directory.getAbsolutePath() + "/";

            try {
                new File(path).mkdirs();
                LevelWriter levelWriter = new LevelWriter(
                        modifiedLevel,
                        new File(path + levelNameModifier.apply(originalFile.getName()))
                );

                levelWriter.write();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void output(String path) {
        output(new File(path));
    }


    /**
     * 不可变的一对。参见 {@link kotlin.Pair}
     *
     * @param <A> 第一个
     * @param <B> 第二个
     */
    private class Pair<A, B> {
        private final A first;
        private final B second;

        Pair(A a, B b) {
            first = a;
            second = b;
        }

        B getSecond() {
            return second;
        }

        A getFirst() {
            return first;

        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }

        /**
         * IDEA AUTO-GENERATED
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (!getFirst().equals(pair.getFirst())) return false;
            return getSecond().equals(pair.getSecond());
        }

        public kotlin.Pair<A, B> toKotlinPair() {
            return new kotlin.Pair<>(first, second);
        }

    }

}

