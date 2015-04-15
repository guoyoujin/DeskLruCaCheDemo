Upvoters = new Mongo.Collection('upvoters');

Meteor.methods({
    upvote: function(postId) {
        check(this.userId, String);
        check(postId, String);
        var post = Posts.findOne(postId);
        if (!post)
            throw new Meteor.Error('invalid', 'Post not found');

        if(post._id !== this.userId) {
            var upvoter = Upvoters.findOne({post_id:post._id,upvoter_id:this.userId});
            if (!upvoter){
               var upvoter = {
                   upvoter_id:this.userId,
                   post_id: post._id
               }

                Posts.update(post._id, {
                    $inc: {votes: 1}
                });

                Upvoters.insert(upvoter);
            }
        }
    }
});