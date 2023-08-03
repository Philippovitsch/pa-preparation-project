import { TagModel } from "./TagModel"
import {UserModel} from "./UserModel.ts";

export type TaskResponseModel = {
  id?: number,
  user: UserModel,
  name: string,
  description: string,
  timestamp: string,
  tags: TagModel[],
  imageName: string,
  imageType: string,
  imageData: string,
  isDone: boolean
}
