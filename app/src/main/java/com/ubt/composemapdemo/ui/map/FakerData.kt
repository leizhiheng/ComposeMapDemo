package com.ubt.composemapdemo.ui.map


import android.graphics.PointF
import com.ubtrobot.mapview.*

// C
val cRoomOutline = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(704f, 562f)
    lineTo(710f, 990f)
    lineTo(704f, 1263f)
    lineTo(798f, 1263f)
    lineTo(806f, 1255f)
    lineTo(829f, 1272f)
    lineTo(838f, 1308f)
    lineTo(857f, 1301f)
    lineTo(869f, 1315f)
    lineTo(880f, 1311f)
    lineTo(916f, 1351f)
    lineTo(933f, 1347f)
    lineTo(979f, 1400f)
    lineTo(1029f, 1442f)
    lineTo(1018f, 1300f)
    lineTo(980f, 1292f)
    lineTo(970f, 1282f)
    lineTo(915f, 1282f)
    lineTo(901f, 1269f)
    lineTo(912f, 1256f)
    lineTo(1012f, 1256f)
    lineTo(1030f, 1245f)
    lineTo(1030f, 550f)
    lineTo(1000f, 550f)
    lineTo(982f, 522f)
    lineTo(972f, 522f)
    lineTo(963f, 514f)
    lineTo(963f, 489f)
    lineTo(947f, 478f)
    lineTo(963f, 444f)
    lineTo(948f, 419f)
    lineTo(908f, 436f)
    lineTo(914f, 474f)
    lineTo(908f, 490f)
    lineTo(918f, 515f)
    lineTo(914f, 533f)
    lineTo(779f, 541f)
    lineTo(759f, 556f)
    close()
}

val cRoomWall = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(731f, 580f)
    lineTo(731f, 565f)
    lineTo(698f, 565f)
    lineTo(698f, 584f)

    moveTo(698f, 606f)
    lineTo(698f, 622f)

    moveTo(698f, 664f)
    lineTo(698f, 871f)

    moveTo(698f, 984f)
    lineTo(698f, 1264f)

    moveTo(910f, 1264f)
    lineTo(1014f, 1264f)
    lineTo(1014f, 1256f)
    lineTo(1042f, 1256f)
    lineTo(1042f, 550f)

    moveTo(1042f, 1286f)
    lineTo(1042f, 1458f)

    moveTo(971f, 503f)
    lineTo(925f, 503f)

    moveTo(925f, 534f)
    lineTo(865f, 534f)
    lineTo(865f, 561f)
    lineTo(855f, 561f)

    moveTo(845f, 554f)
    lineTo(808f, 554f)
    lineTo(808f, 564f)

    moveTo(797f, 554f)
    lineTo(766f, 554f)
    lineTo(766f, 606f)
    lineTo(731f, 606f)
}

// D
val dRoomOutline = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(43f, 481f)
    lineTo(43f, 796f)
    lineTo(255f, 796f)
    lineTo(255f, 882f)
    lineTo(240f, 882f)
    lineTo(240f, 960f)
    lineTo(360f, 960f)
    lineTo(360f, 481f)
    close()
}

val dRoomWall = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(45f, 480f)
    lineTo(45f, 773f)
    moveTo(45f, 789f)
    lineTo(45f, 799f)
    lineTo(49f, 799f)
    lineTo(49f, 859f)
    lineTo(85f, 859f)
    moveTo(101f, 859f)
    lineTo(143f, 859f)
    moveTo(153f, 851f)
    lineTo(217f, 851f)
    moveTo(196f, 798f)
    lineTo(252f, 798f)
    lineTo(252f, 883f)
    lineTo(237f, 883f)
    lineTo(237f, 962f)
    moveTo(97f, 848f)
    lineTo(97f, 795f)
    moveTo(143f, 829f)
    lineTo(143f, 748f)
    lineTo(153f, 748f)
    lineTo(153f, 806f)
    lineTo(160f, 806f)
    lineTo(160f, 831f)
    moveTo(363f, 872f)
    lineTo(363f, 480f)
    lineTo(43f, 480f)
}

// E
val eRoomOutline = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(373f, 481f)
    lineTo(658f, 481f)
    lineTo(658f, 768f)
    lineTo(633f, 768f)
    lineTo(633f, 868f)
    lineTo(564f, 868f)
    lineTo(544f, 849f)
    lineTo(544f, 788f)
    lineTo(373f, 788f)
    close()
}

val eRoomWall = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(547f, 852f)
    lineTo(547f, 799f)
    lineTo(373f, 799f)
    lineTo(373f, 481f)
    lineTo(603f, 481f)
    moveTo(624f, 476f)
    lineTo(661f, 476f)
    lineTo(661f, 768f)
    moveTo(645f, 768f)
    lineTo(645f, 858f)
}

val eBarrier = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(384f, 496f)
    lineTo(394f, 496f)
    moveTo(400f, 496f)
    lineTo(410f, 496f)
    moveTo(410f, 502f)
    lineTo(410f, 528f)
    moveTo(405f, 554f)
    lineTo(488f, 554f)
    lineTo(488f, 503f)
}

// F
val fRoomOutline = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(407f, 986f)
    lineTo(401f, 1004f)
    lineTo(379f, 1011f)
    lineTo(379f, 1236f)
    lineTo(486f, 1236f)
    lineTo(486f, 1103f)
    lineTo(470f, 1086f)
    lineTo(470f, 984f)
    close()
}

val fRoomWall = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(400f, 987f)
    lineTo(400f, 998f)
    lineTo(373f, 998f)
    lineTo(373f, 1192f)
    moveTo(383f, 1249f)
    lineTo(483f, 1249f)
    moveTo(498f, 1244f)
    lineTo(498f, 1089f)
    moveTo(484f, 1082f)
    lineTo(484f, 998f)
}

// G
val gRoomOutline = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(359f, 1294f)
    lineTo(359f, 1294f)
    lineTo(352f, 1408f)
    lineTo(437f, 1408f)
    lineTo(453f, 1414f)
    lineTo(465f, 1405f)
    lineTo(589f, 1405f)
    lineTo(617f, 1377f)
    lineTo(687f, 1377f)
    lineTo(710f, 1393f)
    lineTo(804f, 1393f)
    lineTo(809f, 1272f)
    lineTo(704f, 1267f)
    lineTo(704f, 1291f)
    close()
}

val gRoomWall = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(698f, 1265f)
    lineTo(698f, 1286f)
    lineTo(349f, 1286f)
    lineTo(349f, 1421f)
    moveTo(361f, 1429f)
    lineTo(372f, 1429f)
    moveTo(428f, 1429f)
    lineTo(442f, 1429f)
    moveTo(583f, 1418f)
    lineTo(598f, 1418f)
    moveTo(617f, 1392f)
    lineTo(691f, 1392f)
    moveTo(697f, 1407f)
    lineTo(797f, 1407f)
    lineTo(797f, 1400f)
    lineTo(823f, 1400f)
    lineTo(823f, 1267f)
    lineTo(814f, 1267f)
    lineTo(814f, 1259f)
    lineTo(820f, 1259f)
}

// H
val hRoomOutline = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(371f, 872f)
    lineTo(371f, 962f)
    lineTo(371f, 979f)
    lineTo(513f, 979f)
    lineTo(526f, 996f)
    lineTo(532f, 1071f)
    lineTo(511f, 1101f)
    lineTo(516f, 1232f)
    lineTo(504f, 1252f)
    lineTo(590f, 1257f)
    lineTo(595f, 1217f)
    lineTo(580f, 1146f)
    lineTo(570f, 1133f)
    lineTo(570f, 1110f)
    lineTo(590f, 1091f)
    lineTo(634f, 1091f)
    lineTo(647f, 1073f)
    lineTo(647f, 1048f)
    lineTo(636f, 1033f)
    lineTo(595f, 1017f)
    lineTo(577f, 988f)
    lineTo(588f, 976f)
    lineTo(704f, 979f)
    lineTo(704f, 875f)
    lineTo(640f, 879f)
    lineTo(633f, 870f)
    lineTo(561f, 870f)
    close()
}

val hRoomWall = androidx.compose.ui.graphics.AndroidPath().apply {
    moveTo(368f, 873f)
    lineTo(562f, 873f)
    moveTo(650f, 873f)
    lineTo(686f, 873f)
    moveTo(708f, 873f)
    lineTo(708f, 883f)
    moveTo(513f, 984f)
    lineTo(513f, 1003f)
    lineTo(519f, 1003f)
    lineTo(519f, 1071f)
    moveTo(502f, 1113f)
    lineTo(502f, 1130f)
    moveTo(509f, 1270f)
    lineTo(609f, 1270f)
    moveTo(614f, 1230f)
    lineTo(614f, 1181f)
    moveTo(588f, 1178f)
    lineTo(588f, 1162f)
    moveTo(588f, 1104f)
    lineTo(639f, 1104f)
    moveTo(661f, 1119f)
    lineTo(661f, 1067f)
    lineTo(657f, 1067f)
    lineTo(657f, 1029f)
    moveTo(590f, 1004f)
    lineTo(590f, 988f)
    moveTo(646f, 988f)
    lineTo(703f, 988f)
}

fun generatePath(): List<PointF> {
    val path = mutableListOf<PointF>()
    for ((index, i) in (713..1025 step 10).withIndex()) {
        if (index % 2 == 0) {
            for (j in 618..1241 step 1) {
                path.add(PointF(i.toFloat(), j.toFloat()))
            }
        } else {
            for (j in 1241 downTo 618 step 1) {
                path.add(PointF(i.toFloat(), j.toFloat()))
            }
        }
    }
    return path
}

object FakerData {

    fun create(): RobotMap {
        return RobotMap(
            id = "id",
            scale = 1,
            width = 1080f,
            height = 1800f,
            name = "优必选 19 F",
            createdTime = System.currentTimeMillis(),
            modifyTime = System.currentTimeMillis(),
            rooms = mutableListOf(
                Room(
                    id = "1",
                    outline = cRoomOutline,
                    name = "C",
                    type = RoomType.BED,
                    number = 1,
                    description = "这是 C 房间",
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Room(
                    id = "2",
                    outline = dRoomOutline,
                    name = "D",
                    type = RoomType.DRAWING,
                    number = 2,
                    description = "这是 D 房间",
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Room(
                    id = "3",
                    outline = eRoomOutline,
                    name = "E",
                    type = RoomType.BATH,
                    number = 3,
                    description = "这是 E 房间",
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Room(
                    id = "4",
                    outline = fRoomOutline,
                    name = "F",
                    type = RoomType.DINING,
                    number = 4,
                    description = "这是 F 房间",
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Room(
                    id = "5",
                    outline = gRoomOutline,
                    name = "G",
                    type = RoomType.KITCHEN,
                    number = 5,
                    description = "这是 G 房间",
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Room(
                    id = "6",
                    outline = hRoomOutline,
                    name = "H",
                    type = RoomType.BED,
                    number = 6,
                    description = "这是 H 房间",
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                )
            ),
            pose = Pose(
                id = "1",
                point = PointF(713f, 618f),
                type = PoseType.SELF,
                width = 0f,
                height = 0f,
                originX = 0f,
                originY = 0f,
                zIndex = 0,
                theta = 0f,
                targets = mutableListOf("1111"),
                name = "name-pose",
                description = "description"
            ),
            docker = Pose(
                id = "1",
                point = PointF(503f, 618f),
                type = PoseType.DOCKER,
                width = 0f,
                height = 0f,
                originX = 0f,
                originY = 0f,
                zIndex = 0,
                theta = 0f,
                targets = null
            ),
            ground = Ground(id = "1", name = null, width = 1f, height = 1f, source = ByteArray(11)),
            description = "地图描述信息",
            barriers = mutableListOf(
                Barrier(
                    id = "1",
                    outline = eBarrier,
                    type = BarrierType.SUNDRIES,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                )
            ),
            walls = mutableListOf(
                Barrier(
                    id = "1",
                    outline = cRoomWall,
                    type = BarrierType.WALL,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Barrier(
                    id = "2",
                    outline = dRoomWall,
                    type = BarrierType.WALL,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Barrier(
                    id = "3",
                    outline = eRoomWall ,
                    type = BarrierType.WALL,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Barrier(
                    id = "4",
                    outline = fRoomWall,
                    type = BarrierType.WALL,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Barrier(
                    id = "5",
                    outline = gRoomWall,
                    type = BarrierType.WALL,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                ),
                Barrier(
                    id = "6",
                    outline = hRoomWall,
                    type = BarrierType.WALL,
                    width = 0f,
                    height = 0f,
                    originX = 0f,
                    originY = 0f,
                    zIndex = 0,
                    theta = 0f,
                    targets = null
                )
            )
        )
    }
}


