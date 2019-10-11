package by.training.module2;

import by.training.module2.model.*;
import by.training.module2.repository.ParagraphRepository;
import by.training.module2.repository.TextRepository;
import by.training.module2.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TextServiceTest {

    private List<TextComposite> sentences;
    private ParagraphService service;
    private TextRepository<TextComposite> repository;
    private List<TextComposite> expectedForAsc;
    private List<TextComposite> expectedForDesc;

    @Before
    public void init () {
        sentences = new ArrayList<>();
        expectedForAsc = new ArrayList<>();
        expectedForDesc = new ArrayList<>();
        repository = new ParagraphRepository();
        service = new ParagraphService(repository);
        SentenceComposite sentenceComposite1 = new SentenceComposite(false);
        sentenceComposite1.addText(new WordLeaf("", "one", ""));
        sentenceComposite1.addText(new WordLeaf("", "two", ""));
        sentenceComposite1.addText(new WordLeaf("", "three", ""));
        SentenceComposite sentenceComposite2 = new SentenceComposite(false);
        sentenceComposite2.addText(new WordLeaf("", "one", ""));
        sentences.add(sentenceComposite1);
        sentences.add(sentenceComposite2);
        expectedForAsc.add(sentenceComposite2);
        expectedForAsc.add(sentenceComposite1);
        expectedForDesc.add(sentenceComposite1);
        expectedForDesc.add(sentenceComposite2);
    }

    @Test
    public  void shouldSortInAscendingOrder () {
        service.sort(sentences, new WordLeafSizeComparator(true));
        assertEquals(expectedForAsc, sentences);
    }

    @Test
    public  void shouldSortInDescendingOrder () {
        service.sort(sentences, new WordLeafSizeComparator(false));
        assertEquals(expectedForDesc, sentences);
    }

}
