/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.basicdataset;

import ai.djl.modality.cv.Rectangle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import java.util.List;

/** A metadata class that represents the structure of annotations in Coco. */
public class CocoMetadata {

    public static final Gson GSON =
            new GsonBuilder()
                    .registerTypeAdapter(Rectangle.class, new RectangleDeserializer())
                    .create();

    private List<Image> images;
    private List<Annotation> annotations;
    private List<Category> categories;

    /**
     * Returns a list of all annotations.
     *
     * @return a list of all annotations
     */
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * Returns a list of all categories.
     *
     * @return a list of all categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Returns a list of all images.
     *
     * @return a list of all images
     */
    public List<Image> getImages() {
        return images;
    }

    public static final class Annotation {

        @SerializedName("image_id")
        private long imageId;

        private long id;

        @SerializedName("bbox")
        private Rectangle bBox;

        private double area;

        @SerializedName("category_id")
        private long categoryId;

        public long getImageId() {
            return imageId;
        }

        /**
         * Returns the id of this annotation.
         *
         * @return the id of this annotation
         */
        public long getId() {
            return id;
        }

        /**
         * Returns the bounding box of this annotation.
         *
         * @return the bounding box of this annotation
         */
        public Rectangle getBoundingBox() {
            return bBox;
        }

        /**
         * Returns the category id of this annotation.
         *
         * @return the category id of this annotation
         */
        public long getCategoryId() {
            return categoryId;
        }

        /**
         * Returns the area of this annotation.
         *
         * @return the area of this annotation
         */
        public double getArea() {
            return area;
        }
    }

    public static final class Image {

        private int id;

        @SerializedName("coco_url")
        private String cocoUrl;

        private int height;
        private int width;

        /**
         * Returns the id of this image.
         *
         * @return the id of this image
         */
        public long getId() {
            return id;
        }

        /**
         * Returns the url of this image.
         *
         * @return the url of this image
         */
        public String getCocoUrl() {
            return cocoUrl;
        }

        /**
         * Returns the height of this image.
         *
         * @return the height of this image
         */
        public int getHeight() {
            return height;
        }

        /**
         * Returns the width of this image.
         *
         * @return the width of this image
         */
        public int getWidth() {
            return width;
        }
    }

    public static final class Category {

        private long id;

        /**
         * Returns the id of this category.
         *
         * @return the id of this category
         */
        public long getId() {
            return id;
        }
    }

    public static final class RectangleDeserializer implements JsonDeserializer<Rectangle> {

        /** {@inheritDoc} */
        @Override
        public Rectangle deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
            JsonArray array = json.getAsJsonArray();
            return new Rectangle(
                    array.get(0).getAsDouble(), array.get(1).getAsDouble(),
                    array.get(2).getAsDouble(), array.get(3).getAsDouble());
        }
    }
}
