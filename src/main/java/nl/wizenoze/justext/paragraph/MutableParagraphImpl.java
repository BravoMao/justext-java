/**
 * Copyright (c) 2016-present WizeNoze B.V. All rights reserved.
 *
 * This file is part of justext-java.
 *
 * justext-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * justext-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.wizenoze.justext.paragraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import nl.wizenoze.justext.Classification;
import nl.wizenoze.justext.util.StringPool;
import nl.wizenoze.justext.util.TextUtil;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lcsontos on 1/8/16.
 */
final class MutableParagraphImpl extends BaseParagraph implements MutableParagraph {

    private int charsInLinksCount = 0;
    private Classification classification;
    private String domPath;
    private boolean isBoilerplace = false;
    private boolean isHeading = false;
    private int tagsCount = 0;
    private String text;
    private List<String> textNodes;
    private String[] words;
    private String xpath;

    /**
     * Creates an empty paragraph with the given path info.
     * @param pathInfo path info.
     */
    MutableParagraphImpl(PathInfo pathInfo) {
        this(pathInfo, null, 0, 0);
    }

    /**
     * Creates a paragraph with the given path info, text nodes, character count in links and tags count.
     * @param pathInfo path info
     * @param textNodes text nodes
     * @param charsInLinksCount character count in links
     * @param tagsCount tags count
     */
    MutableParagraphImpl(PathInfo pathInfo, List<String> textNodes, int charsInLinksCount, int tagsCount) {
        if (pathInfo != null) {
            domPath = pathInfo.dom();
            xpath = pathInfo.xpath();
        }

        this.charsInLinksCount = charsInLinksCount;
        this.tagsCount = tagsCount;

        if (textNodes == null) {
            textNodes = new ArrayList<>();
        }

        this.textNodes = textNodes;
    }

    /**
     * Creates a paragraph with the given text nodes.
     * @param textNodes text nodes.
     */
    MutableParagraphImpl(List<String> textNodes) {
        this(null, textNodes, 0, 0);
    }

    /**
     * Creates a paragraph with the given text nodes and character count in links.
     * @param textNodes text nodes.
     * @param charsInLinksCount count of characters in links.
     */
    MutableParagraphImpl(List<String> textNodes, int charsInLinksCount) {
        this(null, textNodes, charsInLinksCount, 0);
    }

    MutableParagraphImpl(Classification classification) {
        this(null, null, 0, 0);
        this.classification = classification;
    }

    @Override
    public String appendText(String text) {
        reset();

        text = TextUtil.normalizeWhiteSpaces(text);

        textNodes.add(text);

        return text;
    }

    @Override
    public void decrementTagsCount() {
        tagsCount--;
    }

    @Override
    public int getCharsInLinksCount() {
        return charsInLinksCount;
    }

    @Override
    public Classification getClassification() {
        return classification;
    }

    @Override
    public String getDomPath() {
        return domPath;
    }

    @Override
    public float getLinkDensity() {
        int textLength = length();

        if (textLength == 0) {
            return 0;
        }

        return 1.0f * charsInLinksCount / textLength;
    }

    @Override
    public int getStopWordsCount(Set<String> stopWords) {
        return getStopWordsCount(words(), stopWords);
    }

    @Override
    public float getStopWordsDensity(Set<String> stopWords) {
        return getStopWordsDensity(words(), stopWords);
    }

    @Override
    public int getTagsCount() {
        return tagsCount;
    }

    @Override
    public String getText() {
        if (text == null) {
            text = StringUtils.join(textNodes, StringPool.EMPTY);
            text = TextUtil.normalizeWhiteSpaces(text.trim());
        }

        return text;
    }

    @Override
    public List<String> getTextNodes() {
        return textNodes;
    }

    @Override
    public List<String> getWords() {
        return Collections.unmodifiableList(Arrays.asList(words()));
    }

    @Override
    public int getWordsCount() {
        return words().length;
    }

    @Override
    public String getXpath() {
        return xpath;
    }

    @Override
    public boolean hasText() {
        return StringUtils.isNotBlank(getText());
    }

    @Override
    public void incrementCharsInLinksCount(int delta) {
        charsInLinksCount += delta;
    }

    @Override
    public void incrementTagsCount() {
        tagsCount++;
    }

    @Override
    public boolean isBoilerplace() {
        return isBoilerplace;
    }

    @Override
    public boolean isHeading() {
        return isHeading;
    }

    @Override
    public int length() {
        return getText().length();
    }

    /**
     * Sets count of characters in links.
     * @param charsInLinksCount count of characters in links.
     */
    public void setCharsInLinksCount(int charsInLinksCount) {
        this.charsInLinksCount = charsInLinksCount;
    }

    @Override
    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    /**
     * Sets DOM path.
     * @param domPath DOM path.
     */
    public void setDomPath(String domPath) {
        this.domPath = domPath;
    }

    /**
     * Sets if this paragraph is boilerplate.
     * @param isBoilerplace true if boilerplate, false otherwise.
     */
    public void setIsBoilerplace(boolean isBoilerplace) {
        this.isBoilerplace = isBoilerplace;
    }

    /**
     * Sets if this paragraph is heading.
     * @param isHeading true if heading, false otherwise.
     */
    public void setIsHeading(boolean isHeading) {
        this.isHeading = isHeading;
    }

    /**
     * Sets tags count.
     * @param tagsCount tags count.
     */
    public void setTagsCount(int tagsCount) {
        this.tagsCount = tagsCount;
    }

    /**
     * Sets text nodes.
     * @param textNodes text nodes.
     */
    public void setTextNodes(List<String> textNodes) {
        reset();

        this.textNodes = textNodes;
    }

    /**
     * Sets XPath.
     * @param xpath xpath.
     */
    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    private void reset() {
        text = null;
        words = null;
    }

    private String[] words() {
        if (words == null) {
            words = StringUtils.split(getText());
        }

        return words;
    }

}