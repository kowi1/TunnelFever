/*
 * Copyright (C) Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
#include "anim.hpp"
#include "util.hpp"
#include "engine.hpp"
#include "obstacle_generator.hpp"
#include "obstacle.hpp"
#include "sfxman.hpp"
#include "shape_renderer.hpp"
#include "text_renderer.hpp"
#include "textured_teapot_render.hpp"
#include "util.hpp"
#include "HelloWorld.hpp"

void RenderBackgroundAnimation(ShapeRenderer *r) {
    float aspect = SceneManager::GetInstance()->GetScreenAspect();
    static const int BG_RECTS = 100;
    static const float RECT_W = 0.1f;
    static const float RECT_H = 0.1f;
    static float rectX[BG_RECTS];
    static float rectY[BG_RECTS];
    static bool rectsInitted = false;
    int i;

    if (!rectsInitted) {
        for (i = 0; i < BG_RECTS; i++) {
            rectX[i] = aspect * (Random(100) / 100.0f);
            rectY[i] = Random(100) / 100.0f;
        }
        rectsInitted = true;
    }

    glClear(GL_COLOR_BUFFER_BIT);

    for (i = 0; i < BG_RECTS; i++) {
        float c = 0.1f + 0.1f * (i % 4);
        r->SetColor(c, c, c);
        r->RenderRect(rectX[i], rectY[i], RECT_W, RECT_H);

        rectX[i] -= (0.01f + 0.01f * (i % 4));
        if (rectX[i] < -RECT_W * 0.5f) {
            rectX[i] = aspect + RECT_W * 0.5f;
            rectY[i] = Random(100) / 100.0f;
        }
    }
}

void RenderBackgroundAnimations(ShapeRenderer *r,TexturedTeapotRender * mTeapotRenderer) {
    float aspect = SceneManager::GetInstance()->GetScreenAspect();
    static const int BG_RECTS = 100;
    static const float RECT_W = 0.1f;
    static const float RECT_H = 0.1f;
    static float rectX[BG_RECTS];
    static float rectY[BG_RECTS];
    static bool rectsInitted = false;
    int i;


    glm::vec3 upVec = glm::vec3(-sin(0.0f), 0, cos(0.0f));
    glm::mat4 mProjMat = glm::perspective(RENDER_FOV, aspect, RENDER_NEAR_CLIP,
                                RENDER_FAR_CLIP);

    glm::mat4  mViewMat = glm::lookAt(glm::vec3 (0.0f,0.0f,0.0f),glm::vec3 (0.0f,0.0f,0.0f), upVec);


    float angleInRadians = glm::radians(-100.0f); // Angle in radians
    glm::vec3 rotationAxis = glm::vec3(0.0f, 0.0f, 1.0f);
    float angleInRadiansz = glm::radians(40.0f); // Angle in radians
    glm::vec3 rotationAxisz = glm::vec3(0.0f, 1.0f, 0.0f);
    glm::mat4 rotationMatrix = glm::rotate(glm::mat4(1.0f), angleInRadians, rotationAxis);
    glm::mat4 rotationMatrixz = glm::rotate(glm::mat4(1.0f), angleInRadiansz, rotationAxisz);
    glm::mat4 modelMat = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 0.0f, 0.0f));
    modelMat = glm::scale(modelMat, glm::vec3(0.35f, 0.35f, 0.35f));
    //mTeapotRenderer->Render(mViewMat*modelMat*rotationMatrix*rotationMatrixz, mProjMat);

    if (!rectsInitted) {
        for (i = 0; i < BG_RECTS; i++) {
            rectX[i] = aspect * (Random(100) / 100.0f);
            rectY[i] = Random(100) / 100.0f;
        }
        rectsInitted = true;
    }

    glClear(GL_COLOR_BUFFER_BIT);

    for (i = 0; i < BG_RECTS; i++) {
        float c = 0.1f + 0.1f * (i % 4);
        r->SetColor(c, c, c);
        r->RenderRect(rectX[i], rectY[i], RECT_W, RECT_H);

        rectX[i] -= (0.01f + 0.01f * (i % 4));
        if (rectX[i] < -RECT_W * 0.5f) {
            rectX[i] = aspect + RECT_W * 0.5f;
            rectY[i] = Random(100) / 100.0f;
        }
    }
}
